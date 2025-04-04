import React, { useState, useEffect } from "react";
import { Outlet, useLocation, useNavigate, Link as RouterLink } from "react-router-dom";
import { DashboardLayout, PageContainer } from "@toolpad/core";
import MenuOutlinedIcon from "@mui/icons-material/MenuOutlined";
import { Box, MenuItem, Select, Typography, Breadcrumbs, Link, Stack } from "@mui/material";
import { useApiData } from '../hooks/useApiData';

const MainLayout = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [currentView, setCurrentView] = useState(location.pathname.startsWith("/grid") ? "grid" : "default");
    const hideSidebar = location.pathname.startsWith("/grid");
    const [visitedPages, setVisitedPages] = useState(location.pathname === "/home" || location.pathname === "/grid" ? [] : [location.pathname]);

    const { data, isLoading, error } = useApiData('');

    console.log(data)

    const NAVIGATION = !isLoading && !error && data?.data?.results
        ? data.data.results.map((view, index) => ({
            kind: 'link',
            title: view.endpoint,
            segment: `information/${view.endpoint.replaceAll(' ', '_')}`,
            icon: <MenuOutlinedIcon />
        }))
        : [{ kind: 'link', title: 'Loading...', segment: 'home', icon: <MenuOutlinedIcon /> }];

    useEffect(() => {
        setVisitedPages((prevVisitedPages) => {
            if (location.pathname === "/home" || location.pathname === "/grid") {
                return [];
            }
            const pathParts = location.pathname.split("/information/");
            const secondPart = pathParts[1] ? `/information/${pathParts[1]}` : location.pathname;
            if (!prevVisitedPages.includes(secondPart)) {
                return [...prevVisitedPages, secondPart];
            }
            return prevVisitedPages;
        });
    }, [location]);

    const handleViewChange = (event) => {
        const selectedView = event.target.value;
        setCurrentView(selectedView);
        if (selectedView === "grid") {
            navigate(`/grid`);
        } else if (selectedView === "default") {
            navigate(`/home`);
        }
    };

    const handleBreadcrumbClick = (path) => {
        setVisitedPages((prevVisitedPages) => {
            const index = prevVisitedPages.indexOf(path);
            if (index !== -1) {
                return prevVisitedPages.slice(0, index + 1);
            }
            return prevVisitedPages;
        });
        navigate(path);
    };

    const visiblePages = visitedPages.length > 3 ? visitedPages.slice(-2) : visitedPages;

    return (
        <Box sx={{
            '& .MuiDrawer-root .MuiDrawer-paper, & [role="navigation"]': {
                '&::-webkit-scrollbar': { display: 'none' },
                msOverflowStyle: 'none',
                scrollbarWidth: 'none',
                overflow: '-moz-scrollbars-none'
            },
            '& .MuiDrawer-root .MuiDrawer-paper *, & [role="navigation"] *': {
                '&::-webkit-scrollbar': { display: 'none' },
                msOverflowStyle: 'none',
                scrollbarWidth: 'none',
                overflow: '-moz-scrollbars-none'
            }
        }}>
            <DashboardLayout
                navigation={NAVIGATION}
                hideNavigation={hideSidebar}
                disableCollapsibleSidebar={hideSidebar}
                slots={{
                    appTitle: () => (
                        <Stack direction='row' alignItems='center' spacing={5}>
                            <Box sx={{
                                display: 'flex',
                                alignItems: 'center',
                                '& .MuiSvgIcon-root': { cursor: 'pointer' },
                                height: '40px'
                            }}>
                                <img src="/logo.svg" alt="I3S" color={"inherit"} width={'100%'} height={'100%'}/>
                            </Box>
                            <Breadcrumbs separator=">" aria-label="breadcrumb">
                                <Link component={RouterLink} to={currentView === "grid" ? "/grid" : "/home"} color="inherit">
                                    {currentView === "grid" ? "Grid" : "Home"}
                                </Link>
                                {visitedPages.length > 3 && (
                                    <Typography color="textPrimary">...</Typography>
                                )}
                                {visiblePages.map((page, index) => (
                                    <Link
                                        component="button"
                                        color="inherit"
                                        onClick={() => handleBreadcrumbClick(page)}
                                        key={page}
                                    >
                                        {page}
                                    </Link>
                                ))}
                            </Breadcrumbs>
                        </Stack>),
                    toolbarActions: () => (
                        <Box sx={{
                            display: 'flex',
                            alignItems: 'center',
                            '& .MuiSvgIcon-root': { cursor: 'pointer' }
                        }}>
                            <Box display="flex" alignItems="center">
                                <Select
                                    value={currentView}
                                    onChange={handleViewChange}
                                    sx={{
                                        '& .MuiOutlinedInput-notchedOutline': { border: 'none' },
                                        '&.MuiOutlinedInput-root': {
                                            minWidth: 120,
                                            backgroundColor: 'transparent',
                                            '&:hover .MuiOutlinedInput-notchedOutline': { border: 'none' },
                                            '&.Mui-focused .MuiOutlinedInput-notchedOutline': { border: 'none' }
                                        },
                                        '& .MuiSelect-select': {
                                            padding: '8px 14px',
                                            borderRadius: '8px',
                                            '&:hover': { backgroundColor: 'rgba(0, 0, 0, 0.04)' }
                                        },
                                        '& .MuiSelect-icon': { color: 'inherit' }
                                    }}
                                    MenuProps={{
                                        PaperProps: {
                                            sx: {
                                                backgroundColor: '#fff',
                                                borderRadius: '8px',
                                                boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
                                                mt: 1,
                                                '& .MuiMenuItem-root': {
                                                    padding: '10px 16px',
                                                    '&:hover': { backgroundColor: 'rgba(0, 0, 0, 0.04)' },
                                                    '&.Mui-selected': {
                                                        backgroundColor: 'rgba(0, 0, 0, 0.08)',
                                                        '&:hover': { backgroundColor: 'rgba(0, 0, 0, 0.12)' }
                                                    }
                                                }
                                            }
                                        }
                                    }}
                                    variant="outlined"
                                >
                                    <MenuItem value="default" sx={{ fontSize: "14px" }}>Default Layout</MenuItem>
                                    <MenuItem value="grid" sx={{ fontSize: "14px" }}>Grid Layout</MenuItem>
                                </Select>
                            </Box>
                        </Box>
                    )
                }}
            >
                <PageContainer>
                    <Outlet />
                </PageContainer>
            </DashboardLayout>
        </Box>
    );
};

export default MainLayout;