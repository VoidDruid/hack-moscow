import React from 'react';
import {MainComp} from './elements/Main';
import SignIn from './elements/SignIn';
import SignUp from './elements/SignUp';
import {About} from './elements/About';

export const routes = [
    {
        id: 0,
        label: "North Star",
        route: "/",
        component: () => <MainComp/>,
    },
    {
        id: 1,
        label: "Sign In",
        route: "/signin",
        component: (history) => <SignIn history></SignIn>
    },
    {
        id: 2,
        label: "Sign Up",
        route: "/signup",
        component: () => <SignUp></SignUp>
    },
    {
        id: 3,
        label: "About project",
        route: "/about",
        component: () => <About></About>
    },
]