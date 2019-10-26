import React from "react";
import {Redirect, Link, Switch, Route, BrowserRouter} from "react-router-dom";
import './style.css'
import {HMap} from "./shared/HMap/HMap";
import { longStackSupport } from "q";

const routesList = [
    {
        path: '/',
        visible: true,
        label: 'main',
        component: () => <div>main</div>
    },
    {
        path: '/settings',
        visible: true,
        label: 'settings',
        component: () => <div>settings</div>
    },
    {
        path: '/map',
        visible: true,
        label: 'map',
        component: () => <div>map</div>
    },
    {
        path: '/statistics',
        visible: true,
        label: 'statistics',
        component: () => <div>statistics</div>
    },
    {
        path: '/login',
        component: () => <div>login</div>
    },
    {
        path: '/logout',
        visible: true,
        label: 'logout',
        component: () => {
            localStorage.setItem('isLogin', false)
            return <Redirect to={'/login'} />
        }
    }
]

export const Root = () => <div className="root-container">
    <BrowserRouter>
        <div className="root-menu">
            {routesList.map(
                route => route.visible && <Link key={1 + route.label} to={route.path}>
                    {route.label}
                    <br/>
                </Link>
            )}
        </div>
        <div className='root-content'>
            <HMap app_id={process.env.APP_ID}
                  app_code={process.env.APP_CODE}
                  zoom={10} lat={55.751244}
                  lng={ 37.618423}
            />
        </div>
        <div className='root-tool'>
        <Switch>
            {
                routesList.map(
                    route => <Route key={2 + route.label} path={route.path} exact component={route.component}/>

                )
            }
        </Switch>
        </div>
    </BrowserRouter>
</div>;
