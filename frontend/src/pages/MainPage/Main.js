import React from 'react';

import './styles.css';

export const Main = () => {
    return (
        <div className="main-page-box">
            <header>App Main Fields</header>
            <main>
                <section className="main-page-section">
                    <div className="main-page-header">Events</div>
                    <div className="main-page-description">Provide visualisated data of events being organised on chosen location</div>
                </section>
                <section className="main-page-section">
                    <div className="main-page-header">Statistics</div>
                    <div className="main-page-description">Show you statistical information of actual city crowding state</div>
                </section>
                <section className="main-page-section">
                    <div className="main-page-header">Settings</div>
                    <div className="main-page-description">Personal setting for your account defining data of visualisation</div>
                </section>
            </main>
        </div>
    );
}