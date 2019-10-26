import React from 'react';
import { MapContainer } from './MapContainer/MapContainer';
import { hereIsolineUrl, maxIsolineRangeLookup } from './here';
import {observer} from 'mobx-react'
import {observable} from "mobx";

@observer
class MapsWrapper extends React.Component {
    mapConfig = {
        name: 'Seattle, WA',
        coordinates: [47.605779, -122.315744],
        polygon: []
    }
    @observable mapOptions= {
        zoom: 5,
        type: 'distance',
        range: 300000,
        mode: 'car',
        traffic: 'disabled',
        style: 'reduced.day'
    }

    render() {
        console.log(this.mapOptions)
        const max = this.mapOptions.type === 'distance' ?
            maxIsolineRangeLookup.distance :
            maxIsolineRangeLookup.time;

        const sliderVal = this.mapOptions.range > max ? max : this.mapOptions.range;

        return (
            <div>
            <MapContainer
                center={this.mapConfig.coordinates}
                options={this.mapOptions}
                handleDrag={this.handleDrag}
                polygon={this.mapConfig.polygon}
                style={this.mapOptions.style}
            />
                <button onClick={() => this.mapOptions.zoom++}>click! </button>
            </div>
        );
    }
}
export default MapsWrapper;
