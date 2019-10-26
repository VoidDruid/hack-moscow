import React from "react";
import {Redirect, Link, Switch, Route, BrowserRouter, NavLink} from "react-router-dom";
import './style.css'
import {CheckboxIcon, HomeIcon, MapIcon, SettingsIcon} from "./shared/icons";
import MapsWrapper from "./shared/MapWrapper";
import EventsPage from "./pages/EventsPage";


const routesList = [
    {
        path: '/',
        visible: true,
        icon: <HomeIcon />,
        label: 'main',
        component: () => <div>main</div>
    },
    {
        path: '/map',
        visible: true,
        icon: <CheckboxIcon />,
        label: 'Set event',
        component: () => <EventsPage />
    },
    {
        path: '/statistics',
        visible: true,
        icon: <MapIcon />,
        label: 'statistics',
        component: () => <div>statistics</div>
    },
    {
        path: '/login',
        component: () => <div>login</div>
    },
    {
        path: '/settings',
        visible: true,
        icon: <SettingsIcon />,
        label: 'settings',
        component: () => <div>settings</div>
    },
    {
        path: '/logout',
        visible:true,
        label: 'logout',
        component: () => {
            alert('logout');
            return <Redirect to={'/login'} />
        }
    }
]

export const Root = () => <div className="root-container">
    <BrowserRouter>
        <div className="root-menu">
            <div className='root-menu-item'>North Star</div>
            {routesList.map(

                route => route.visible && <div className='root-menu-item'>
                    {route.icon && <span className='root-menu-item-icon'>{route.icon}</span>}
                    <div className='root-menu-item-link'>
                        <NavLink key={1 + route.label} to={route.path} activeClassName='active'>
                            {route.label}
                        </NavLink>
                    </div>
                </div>
            )}
        </div>
        <div className='root-content'>
            <MapsWrapper
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
