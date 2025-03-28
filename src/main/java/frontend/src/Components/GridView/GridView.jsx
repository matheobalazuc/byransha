import { useNavigate } from 'react-router-dom';
import { Box, Card, CardContent, CircularProgress, Grid2, Typography } from '@mui/material';
import React from 'react';
import { View } from "../Common/View.jsx";
import { useTitle } from "../../global/useTitle.jsx";
import {useApiData} from '../../hooks/useApiData';

const GridView = () => {
    const navigate = useNavigate();
    useTitle("Views");
    const { data, isLoading, error } = useApiData('current_node');

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