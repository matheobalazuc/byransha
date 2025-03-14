import React from 'react';
import './HomePage.css';
import { Link } from "react-router-dom";
import CircularProgress from "@mui/material/CircularProgress";
import { useTitle } from "../../global/useTitle";
import { useApiData } from '../../hooks/useApiData';

const HomePage = () => {
    const { data, isLoading } = useApiData('GetNodeInfo');
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

    if (!data || !data.result) {
        return <div>Error: Data is null.</div>;
    }

    return (
        <div className="home-page">
            <h1>Vue du Noeud Courant</h1>
            <h2>ID: {data.result.id}</h2>
            <h3>Vues Disponibles:</h3>
            <ul>
                {data.result.views.map((view, index) => (
                    <li key={index}>
                        <Link to={`/information/${index}`}>
                            <strong>{view.name}</strong>: {view.contentType}
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default HomePage;