import { useNavigate } from 'react-router-dom';
import {Box, Button, Card, CardContent, CircularProgress, Grid2, Typography} from '@mui/material';
import React, {useCallback} from 'react';
import { View } from "../Common/View.jsx";
import { useTitle } from "../../global/useTitle.jsx";
import {useApiData, useApiMutation} from '../../hooks/useApiData';

const GridView = () => {
    const navigate = useNavigate();
    useTitle("Views");
    const { data, isLoading, error, refetch } = useApiData('current_node');
    const { data: navData, isLoading: navIsLoading, error: navIsError, refetch: refetchNav } = useApiData('nav2bnode_nav2');
    const jumpMutation = useApiMutation('jump', {
        onSuccess: (data, variables, context) => {
            refetch()
            refetchNav()
        },
    })

    const jumpToNode = useCallback((nodeId) => {
        jumpMutation.mutate(`target=${nodeId}`)
    }, [])

    if (isLoading) {
        return (
            <Box
                sx={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    height: "100vh",
                }}
            >
                <CircularProgress />
            </Box>
        );
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    if (!data || !data.data || !data.data.results) {
        return <div>Error: Data is null.</div>;
    }

    const views = data.data.results[0].result.data.views.data || [];

    return (
        <Box sx={{ padding: { xs: '5px', md: '40px' }, maxWidth: '100%', margin: '0 auto' }}>
            {
                navIsLoading && <CircularProgress />
            }
            <Box>
                {
                    navData && Object.keys(navData.data.results[0].result.data.ins).map((inNode) => {
                        return <Button onClick={() => jumpToNode(navData.data.results[0].result.data.ins[inNode])} variant="contained">{inNode} ({navData.data.results[0].result.data.ins[inNode]})</Button>
                    })
                }
            </Box>
            <Box sx={{ paddingY: '10px'}}>
                {
                    navData && Object.keys(navData.data.results[0].result.data.outs).map((outNode) => {
                        return <Button onClick={() => jumpToNode(navData.data.results[0].result.data.outs[outNode])} variant="contained">{outNode} ({navData.data.results[0].result.data.outs[outNode]})</Button>
                    })
                }
            </Box>
            <Typography variant="h4" gutterBottom sx={{ marginBottom: '32px' }}>
                Views
            </Typography>
            <Grid2 container spacing={4}>
                {views.map((view, index) => (
                    <Grid2 size={{ xs: 12, sm: 6 }} key={index}>
                        <Card
                            sx={{
                                cursor: 'pointer',
                                aspectRatio: '1',
                                transition: 'transform 0.2s, box-shadow 0.2s',
                                '&:hover': {
                                    transform: 'translateY(-4px)',
                                    boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
                                },
                                display: 'flex',
                                flexDirection: 'column',
                            }}
                            onClick={() => navigate(`/information/${view.label.replaceAll(' ', '_')}`)}
                        >
                            <CardContent
                                sx={{
                                    padding: '24px',
                                    height: '100%',
                                    display: 'flex',
                                    flexDirection: 'column',
                                    overflow: 'hidden',
                                }}
                            >
                                <Typography variant="h6" sx={{ marginBottom: '16px', flexShrink: 0 }}>
                                    {view.label}
                                </Typography>
                                <Typography
                                    variant="body2"
                                    color="textSecondary"
                                    sx={{
                                        flex: 1,
                                        overflow: 'auto',
                                        msOverflowStyle: 'none',
                                        scrollbarWidth: 'none',
                                        '&::-webkit-scrollbar': { display: 'none' },
                                    }}
                                >
                                    Content:
                                    <View viewId={view.label.replaceAll(' ', '_')} />
                                </Typography>
                            </CardContent>
                        </Card>
                    </Grid2>
                ))}
            </Grid2>
        </Box>
    );
};

export default GridView;
