import React from 'react';
import './HomePage.css';
import { Link } from "react-router-dom";
import CircularProgress from "@mui/material/CircularProgress";
import { useTitle } from "../../global/useTitle";
import {useApiData} from '../../hooks/useApiData';

const HomePage = () => {
    const { data, isLoading } = useApiData('current_node');
    useTitle("Home");

    console.log(data)

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

    if (!data || !data.data || !data.data.results) {
        return <div>Error: Data is null.</div>;
    }

    return (
        <div className="home-page">
            <h1>Vue du Noeud Courant</h1>
            <h3>Vues Disponibles:</h3>
            <ul>
                {data.data.results[0].result.data.views.data.map((view, index) => (
                    <li key={view.id}>
                        <Link to={`/information/${view.label.replaceAll(' ',  '_')}`}>
                            <strong>{view.label}</strong>
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default HomePage;