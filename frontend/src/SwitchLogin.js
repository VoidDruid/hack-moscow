import React from 'react';
import {Root} from './Root';
import {LandingPage} from './unloginfront/LandingPage';
import {ProtectedRoute} from './unloginfront/ProtectedRoute';
import {BrowserRouter,Switch,Route} from 'react-router-dom';
import Auth from './unloginfront/Auth'

export const SwitchLogin = () => {
    return(
        <BrowserRouter>
            <Switch>
                <Route exact path={Auth.isAuthenticated() ? "/ssduaasdkkhjasdjkcsuyyasd72e3727314414eefddscxv" : "/"} component={LandingPage}/>
                <ProtectedRoute exact path={Auth.isAuthenticated() ? "/" : "/ssduaasdkkhjasdjkcsuyyasd72e3727314414eefddscxv"} component={Root}/>
            </Switch>
        </BrowserRouter>
    );
}