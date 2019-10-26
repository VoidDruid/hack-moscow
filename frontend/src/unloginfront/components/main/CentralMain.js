import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import {routes} from './MainRoutes'

import './Main.css';



export const CentralMain = () =>{
    // const routesMap = Routes.map(el => <Route key={el.id} path={el.route} exact component={el.component}></Route>)
    return(
        <div>
            <div className="container-center">
            <Switch>{
                routes.map(el => <Route key={el.id} path={el.route} exact component={el.component} />)
            }</Switch>
            </div>   
        </div>
    );
}