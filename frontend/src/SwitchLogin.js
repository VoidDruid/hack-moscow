import React from 'react';
import {Root} from './Root';
import {LandingPage} from './unloginfront/LandingPage';
import {ProtectedRoute} from './unloginfront/ProtectedRoute';
import {BrowserRouter,Switch,Route} from 'react-router-dom';
import {historyStore} from './store/HistoryStore'
import {observer} from 'mobx-react'

export const SwitchLogin = observer((props) =>
<BrowserRouter>
    <Switch>
        <Route path={historyStore.isAuth ? "/ssduaasdkkhjasdjkcsuyyasd72e3727314414eefddscxv" : "/"} component={LandingPage}/>
        <ProtectedRoute path={historyStore.isAuth ? "/" : "/ssduaasdkkhjasdjkcsuyyasd72e3727314414eefddscxv"} component={Root}/>
    </Switch>
</BrowserRouter>
)
