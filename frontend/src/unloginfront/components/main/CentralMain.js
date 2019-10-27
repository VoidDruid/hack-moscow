import React from 'react';
import {Switch, Route} from 'react-router-dom';
import {routes} from './MainRoutes'



export const CentralMain = (props) =>{
    return(
        <div>
            <div className="container-center">
            <Switch>{
                routes.map(el => <Route key={el.id} path={el.route} exact component={el.component} history={props.history} />)
            }</Switch>
            </div>
        </div>
    );
}
