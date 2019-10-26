import React from 'react';
import {LeftMenu} from './components/main/LeftMenu';
import {CentralMain} from './components/main/CentralMain';
import {BrowserRouter} from 'react-router-dom';

export const LandingPage = () =>{
    return(
        <div className="container-main">
            <BrowserRouter>
                <LeftMenu></LeftMenu>
                <CentralMain></CentralMain>
            </BrowserRouter>
        </div>
    );
}
