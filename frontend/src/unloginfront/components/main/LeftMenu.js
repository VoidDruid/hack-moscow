import React from 'react';
import {Link, BrowserRouter, Route, Switch} from 'react-router-dom';
import {routes} from './MainRoutes'



export const LeftMenu = () =>{
    return(
        <div className="container-left-menu">
            <nav className="navbar">
                    {routes.map(el => <Link key={el.id} to={el.route}>{el.label}</Link>)}
            </nav>
            
        </div>
    );
}