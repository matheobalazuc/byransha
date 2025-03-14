import React from 'react';
import './HomePage.css';
import { Link } from "react-router-dom";
import CircularProgress from "@mui/material/CircularProgress";
import { useTitle } from "../../global/useTitle";
import {useApiData, useMainApiData} from '../../hooks/useApiData';

const HomePage = () => {
    const { data, isLoading } = useMainApiData();
    useTitle("Home");

    if (isLoading) {
        return (
            <div
                style={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    height: "100vh",
                }}
            >
                <CircularProgress />
            </div>
        );
    }

    if (!data || !data.results) {
        return <div>Error: Data is null.</div>;
    }

    return (
        <div className="home-page">
            <h1>Vue du Noeud Courant</h1>
            <h3>Vues Disponibles:</h3>
            <ul>
                {data.results.map((view, index) => (
                    <li key={view.endpoint}>
                        <Link to={`/information/${view.endpoint}`}>
                            <strong>{view.endpoint}</strong>
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default HomePage;