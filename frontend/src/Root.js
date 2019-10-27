import React from "react";
import {Redirect, Link, Switch, Route, BrowserRouter, NavLink} from "react-router-dom";
import './style.css'
import {CheckboxIcon, HomeIcon, MapIcon, SettingsIcon} from "./shared/icons";
import MapsWrapper from "./shared/MapWrapper/MapWrapper";
import EventsPage from "./pages/EventsPage/EventPage";
import Auth from './unloginfront/Auth'
import { historyStore } from "./store/HistoryStore";
import Statistics from './pages/StatisticsPage/Statistics';
import {Main} from './pages/MainPage/Main'


const routesList = [
    {
        path: '/',
        visible: true,
        icon: <HomeIcon />,
        label: 'Main',
        component: () => <Main />
    },
    {
        path: '/map',
        visible: true,
        icon: <CheckboxIcon />,
        label: 'Events',
        component: () => <EventsPage />
    },
    {
        path: '/statistics',
        visible: true,
        icon: <MapIcon />,
        label: 'Statistics',
        component: () => <Statistics></Statistics>
    },
    {
        path: '/settings',
        visible: true,
        icon: <SettingsIcon />,
        label: 'Settings',
        component: () => <div>settings</div>
    },
    {
        path: '/logout',
        visible:true,
        label: 'Logout',
        component: () => {
            historyStore.logOut()
            return <></>
            /*return <Redirect to={{pathname: "/"}} />*/
        }
    },
    {
        path: '/',
        notExact: true,
        component: () =><Redirect to='/' />
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
                    route => <Route key={2 + route.label} path={route.path} exact={!route.notExact} component={route.component}/>
                )
            }
        </Switch>
        </div>
    </BrowserRouter>
</div>;
