import React from 'react';
import { Map, TileLayer, Marker, Polygon, Popup} from 'react-leaflet';
import { hereTileUrl } from '../here';
import {observer} from 'mobx-react'
import {mapStore} from "../../../store/MapStore";
import {rest} from "../../../store/RestStore";
import HeatmapLayer from "react-leaflet-heatmap-layer";
import {observable} from "mobx";
import {eventFormStore} from "../../../store/EventFormStore";
import {statisticsStore} from "../../../store/StatisticsStore";
@observer
class MapContainer extends React.Component {
    constructor(props) {
        super(props);
    }


    handleClick = (e) => {
        console.log(e);
        if (!eventFormStore.loc) {
            mapStore.addEventMarker(e.latlng);
        } else {
            mapStore.editLastEventMarker(e.latlng);
        }
        eventFormStore.setLoc(e.latlng);
    };
    render() {
        return (
            <Map
                className="map"
                center={this.props.center}
                zoom={parseInt(this.props.options.zoom)}
                zoomControl={false}
                attributionControl={this.props.index === 8}
                onClick = {this.handleClick}
            >
                {mapStore.isHeatMap && <HeatmapLayer
                    fitBoundsOnLoad
                    fitBoundsOnUpdate
                    points={statisticsStore.heatPoints}
                    longitudeExtractor={m => m[1]}
                    latitudeExtractor={m => m[0]}
                    intensityExtractor={m => m[2]} />}
                <TileLayer
                    url={hereTileUrl(this.props.style)}
                />


                {mapStore._isMarkersMap && mapStore.eventsMarkers.map((position, idx) =>
                    <Marker key={`marker-${idx}`}
                            position={position}
                            onMouseOver={(e) => {
                                e.target.openPopup();
                            }}
                            onMouseOut={(e) => {
                                e.target.closePopup();
                            }}
                    >
                        <Popup>
                            <span>{mapStore.eventMarkersTitles && mapStore.eventMarkersTitles[idx]}</span>
                        </Popup>
                    </Marker>

                )}

                )
                    }
                {
                    this.props.polygon.length > 0 &&
                    <Polygon
                        positions={this.props.polygon}
                        color="#2DD5C9"
                    />
                }
            </Map>
        )
    }
}
export default MapContainer
