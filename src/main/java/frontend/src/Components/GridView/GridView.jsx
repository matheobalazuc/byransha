import { useNavigate } from 'react-router-dom';
import { Box, Button, Card, CardContent, CircularProgress, Grid2, Typography } from '@mui/material';
import React, { useCallback } from 'react';
import { View } from "../Common/View.jsx";
import { useTitle } from "../../global/useTitle.jsx";
import { useApiData, useApiMutation } from '../../hooks/useApiData';

const GridView = () => {
    const navigate = useNavigate();
    useTitle("Views");
    const { data, isLoading, error, refetch } = useApiData('current_node');
    const { data: navData, isLoading: navIsLoading, error: navIsError, refetch: refetchNav } = useApiData('nav2');
    const jumpMutation = useApiMutation('jump', {
        onSuccess: () => {
            refetch();
            refetchNav();
        },
    });

    const jumpToNode = useCallback((nodeId) => {
        jumpMutation.mutate(`target=${nodeId}`);
    }, []);

    if (isLoading) {
        return (
            <Box
                sx={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    height: "100vh",
                    bgcolor: '#e0e7ff',
                }}
            >
                <CircularProgress sx={{ color: '#1e88e5' }} /> {/* Blue spinner */}
            </Box>
        );
    }

    if (error) {
        return (
            <Box sx={{ bgcolor: '#ffebee', p: 2, borderRadius: 2, color: '#d32f2f', textAlign: 'center' }}>
                Error: {error.message}
            </Box>
        );
    }

    if (!data || !data.data || !data.data.results) {
        return (
            <Box sx={{ bgcolor: '#fff3e0', p: 2, borderRadius: 2, color: '#ef6c00', textAlign: 'center' }}>
                Error: Data is null.
            </Box>
        );
    }

    const views = data.data.results[0].result.data.views.data || [];

    return (
        <Box
            sx={{
                padding: { xs: '10px', md: '40px' },
                maxWidth: '100%',
                margin: '0 auto',
                bgcolor: '#e8eaf6',
                minHeight: '100vh',
            }}
        >
            {navIsLoading && <CircularProgress sx={{ color: '#1e88e5', display: 'block', mx: 'auto' }} />}
            <Box sx={{ mb: 2 }}>
                {navData &&
                    Object.keys(navData.data.results[0].result.data.ins).map((inNode) => (
                        <Button
                            key={inNode}
                            onClick={() => jumpToNode(navData.data.results[0].result.data.ins[inNode])}
                            variant="contained"
                            sx={{
                                bgcolor: '#3949ab',
                                color: '#fff',
                                mr: 1,
                                mb: 1,
                                '&:hover': { bgcolor: '#5c6bc0' },
                            }}
                        >
                            {inNode} ({navData.data.results[0].result.data.ins[inNode]})
                        </Button>
                    ))}
            </Box>
            <Box sx={{ paddingY: '10px' }}>
                {navData &&
                    Object.keys(navData.data.results[0].result.data.outs).map((outNode) => (
                        <Button
                            key={outNode}
                            onClick={() => jumpToNode(navData.data.results[0].result.data.outs[outNode])}
                            variant="contained"
                            sx={{
                                bgcolor: '#00897b',
                                color: '#fff',
                                mr: 1,
                                mb: 1,
                                '&:hover': { bgcolor: '#26a69a' },
                            }}
                        >
                            {outNode} ({navData.data.results[0].result.data.outs[outNode]})
                        </Button>
                    ))}
            </Box>
            <Typography
                variant="h4"
                gutterBottom
                sx={{
                    marginBottom: '32px',
                    color: '#1a237e',
                    fontWeight: 'bold',
                    textAlign: 'center',
                    borderBottom: '2px solid #3f51b5',
                    pb: 1,
                }}
            >
                Views
            </Typography>
            <Grid2 container spacing={4}>
                {views.map((view, index) => (
                    <Grid2 size={{ xs: 12, sm: 6 }} key={index}>
                        <Card
                            sx={{
                                cursor: 'pointer',
                                aspectRatio: '1',
                                transition: 'transform 0.3s ease, box-shadow 0.3s ease',
                                bgcolor: '#ffffff',
                                border: '1px solid #e0e0e0',
                                borderRadius: 2,
                                '&:hover': {
                                    transform: 'translateY(-6px)',
                                    boxShadow: '0 8px 24px rgba(63, 81, 181, 0.2)',
                                    borderColor: '#3f51b5',
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
                                    bgcolor: 'linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)',
                                }}
                            >
                                <Typography
                                    variant="h6"
                                    sx={{
                                        marginBottom: '16px',
                                        flexShrink: 0,
                                        color: '#283593',
                                        fontWeight: '600',
                                    }}
                                >
                                    {view.label}
                                </Typography>
                                <Typography
                                    variant="body2"
                                    sx={{
                                        flex: 1,
                                        overflow: 'auto',
                                        color: '#424242',
                                        msOverflowStyle: 'none',
                                        scrollbarWidth: 'thin',
                                        scrollbarColor: '#3f51b5 #e8eaf6',
                                        '&::-webkit-scrollbar': { width: '6px' },
                                        '&::-webkit-scrollbar-thumb': {
                                            bgcolor: '#3f51b5',
                                            borderRadius: '3px',
                                        },
                                        '&::-webkit-scrollbar-track': { bgcolor: '#e8eaf6' },
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