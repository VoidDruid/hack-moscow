import React from 'react';
import mapIcon from './img/map-icon.svg';
import checkBoxIcon from './img/checkbox-icon.svg';
import settingsIcon from './img/settings-icon.svg';
import flagIcon from './img/flag-icon.svg'
import homeIcon from './img/home-icon.svg'
import {SvgIcon} from "./SvgIcon";

export const MapIcon = (props) => <SvgIcon content={mapIcon} />
export const CheckboxIcon = (props) => <SvgIcon content={checkBoxIcon} />
export const SettingsIcon = (props) => <SvgIcon content={settingsIcon}/>
export const FlagIcon = (props) => <SvgIcon content={flagIcon}/>
export const HomeIcon = (props) => <SvgIcon content={homeIcon}/>
