import React from 'react';
import axios from 'axios';
import './HomePage.css';
import {Link} from "react-router-dom";
import useSWR from 'swr';
import CircularProgress from "@mui/material/CircularProgress";
import {useTitle} from "../../global/useTitle";

const fetcher = url => axios.get(url)

const HomePage = () => {
    const {
        data,
        isLoading: loading
    } = useSWR('https://dronic.i3s.unice.fr:8080/api?endpoint=GetNodeInfo', fetcher);
    useTitle("Home");

    if (loading) {
        return (
            <div
                style={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    height: "100vh",
                }}
            >
                <CircularProgress/>
            </div>
        );
    }

    if (!data) {
        return <div>Error: Data is null.</div>;
    }

    return (
        <div className="home-page">
            <h1>Vue du Noeud Courant</h1>
            <h2>ID: {data.data.result.id}</h2>

            <h3>Vues Disponibles:</h3>
            <ul>
                {data.data.result.views.map((view, index) => (
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