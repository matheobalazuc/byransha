import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import {StyledEngineProvider} from "@mui/material";
import { RouterProvider} from "react-router-dom";
import {router} from "./global/router";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <StyledEngineProvider injectFirst>
                <RouterProvider router={router} />
        </StyledEngineProvider>
    </React.StrictMode>
);


