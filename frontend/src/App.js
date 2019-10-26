import React, {useState} from 'react'
import {Root} from './Root';



export const App = () =>{

    const [isLogin, setLogin] = useState(Boolean(localStorage.getItem('isLogin')) || false);
    if(isLogin === true){
        return(
            <Root />
        );
    }else{
        return(
            <div>
                <button onClick = {() => setLogin(localStorage.setItem('isLogin', !isLogin))}>Click to login</button>
            </div>  
        );
    }

    

}