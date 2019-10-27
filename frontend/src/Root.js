import React from "react";
import {Redirect, Switch, Route, BrowserRouter, NavLink} from "react-router-dom";
import './style.css'
import {CheckboxIcon, ExitIcon, HomeIcon, MapIcon, SettingsIcon} from "./shared/icons";
import MapsWrapper from "./shared/MapWrapper/MapWrapper";
import EventsPage from "./pages/EventsPage/EventPage";
import { historyStore } from "./store/HistoryStore";
import Settings from './pages/SettingsPage/Settings'
import {Main} from './pages/MainPage/Main'
import StatisticsPage from "./pages/StatisticsPage/StatisticsPage";


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
        label: 'StatisticsPage',
        component: () => <StatisticsPage />
    },
    {
        path: '/settings',
        visible: true,
        icon: <SettingsIcon />,
        label: 'Settings',
        component: () => <Settings></Settings>
    },
    {
        path: '/logout',
        visible:true,
        label: 'Logout',
        icon: <ExitIcon/>,
        component: () => {
            historyStore.logOut()
            return <></>
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
