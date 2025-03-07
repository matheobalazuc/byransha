import React, {useState} from "react";
import {Outlet, useLocation, useNavigate} from "react-router-dom";
import {DashboardLayout, PageContainer} from "@toolpad/core";
import useSWR from "swr";
import axios from "axios";
import MenuOutlinedIcon from "@mui/icons-material/MenuOutlined";
import {Box, MenuItem, Select} from "@mui/material";

const fetcher = url => axios.get(url)

const MainLayout = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [currentView, setCurrentView] = useState(location.pathname.startsWith("/grid") ? "grid" : "default");

    // Determine if the current path is `/grid` (GridView page)
    const hideSidebar = location.pathname.startsWith("/grid");

    const {
        data,
        error,
        isLoading: loading
    } = useSWR('https://dronic.i3s.unice.fr:8080/api?endpoint=GetNodeInfo', fetcher);


    const NAVIGATION = !loading && !error ? data.data.result.views.map((view, index) => ({
        kind: 'link',
        title: view.name,
        segment: `information/${index}`,
        icon: <MenuOutlinedIcon/>
    })) : [{
        kind: 'link',
        title: 'Loading...',
        segment: 'home',
        icon: <MenuOutlinedIcon/>
    }];

    const handleViewChange = (event) => {
        const selectedView = event.target.value;
        setCurrentView(selectedView);
        // Navigate to the appropriate page
        if (selectedView === "grid") {
            navigate(`/grid`);
        } else if (selectedView === "default") {
            navigate(`/information/0`);
        }
    };

    return (
        <Box sx={{
            // Selectors to target the sidebar scrollbar
            '& .MuiDrawer-root .MuiDrawer-paper, & [role="navigation"]': {
                '&::-webkit-scrollbar': {
                    display: 'none'
                },
                msOverflowStyle: 'none',
                scrollbarWidth: 'none',
                overflow: '-moz-scrollbars-none'  // Old Firefox
            },
            // Target any potential nested scrollable elements
            '& .MuiDrawer-root .MuiDrawer-paper *, & [role="navigation"] *': {
                '&::-webkit-scrollbar': {
                    display: 'none'
                },
                msOverflowStyle: 'none',
                scrollbarWidth: 'none',
                overflow: '-moz-scrollbars-none'  // Old Firefox
            }
        }}>
            <DashboardLayout navigation={NAVIGATION} hideNavigation={hideSidebar}
                             disableCollapsibleSidebar={hideSidebar}
                             slots={{
                                 toolbarActions: () => {
                                     return (
                                         <>
                                             {/* DROPDOWN VIEW SELECTOR */}
                                             <Box display="flex" alignItems="center">
                                                 <Select
                                                     value={currentView}
                                                     onChange={handleViewChange}
                                                     sx={{
                                                         '& .MuiOutlinedInput-notchedOutline': {
                                                             border: 'none'
                                                         },
                                                         '&.MuiOutlinedInput-root': {
                                                             minWidth: 120,
                                                             backgroundColor: 'transparent',
                                                             '&:hover .MuiOutlinedInput-notchedOutline': {
                                                                 border: 'none'
                                                             },
                                                             '&.Mui-focused .MuiOutlinedInput-notchedOutline': {
                                                                 border: 'none'
                                                             }
                                                         },
                                                         '& .MuiSelect-select': {
                                                             padding: '8px 14px',
                                                             borderRadius: '8px',
                                                             '&:hover': {
                                                                 backgroundColor: 'rgba(0, 0, 0, 0.04)'
                                                             }
                                                         },
                                                         '& .MuiSelect-icon': {
                                                             color: 'inherit'
                                                         }
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
                                                                     '&:hover': {
                                                                         backgroundColor: 'rgba(0, 0, 0, 0.04)'
                                                                     },
                                                                     '&.Mui-selected': {
                                                                         backgroundColor: 'rgba(0, 0, 0, 0.08)',
                                                                         '&:hover': {
                                                                             backgroundColor: 'rgba(0, 0, 0, 0.12)'
                                                                         }
                                                                     }
                                                                 }
                                                             }
                                                         }
                                                     }}
                                                     variant="outlined">
                                                     <MenuItem value="default" sx={{fontSize: "14px"}}>Default
                                                         Layout</MenuItem>
                                                     <MenuItem value="grid" sx={{fontSize: "14px"}}>Grid
                                                         Layout</MenuItem>
                                                 </Select>
                                             </Box>
                                         </>
                                     );
                                 }
                             }}>
                <PageContainer>
                    <Outlet/>
                </PageContainer>
            </DashboardLayout>
        </Box>
    );
};

export default MainLayout;
