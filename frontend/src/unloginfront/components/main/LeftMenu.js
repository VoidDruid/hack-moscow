import React from 'react';
import {Link, BrowserRouter, Route, Switch} from 'react-router-dom';
import {routes} from './MainRoutes'

import './Main.css';



export const LeftMenu = () =>{
    // const routesMapLink = routes.map(el => <Link key={el.id} to={el.route}>{el.label}</Link>)
    /*const routesMapRoute = Routes.map(el => <Route key={el.id} path={el.route} exact ></Route>)*/
    return(
        <div className="container-left-menu">
            <nav className="navbar">
                    {routes.map(el => <Link key={el.id} to={el.route}>{el.label}</Link>)}
            </nav>
            
        </div>
    );
}