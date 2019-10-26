import React from 'react';
import {observer} from "mobx-react";
import {rest} from '../../store/RestStore';
import {mapStore} from "../../store/MapStore";
import './style.css'
import EventsList from "./EventsList/EventsList";
import EventForm from "./EventForm/EventForm";
@observer
class EventsPage extends React.Component{
    // handleClick = () => mapStore.toggleHeatMap()
    componentDidMount() {
        rest.getOrganisationsEvents();
        mapStore.activateMarkersMap();
    }
    componentWillUnmount() {
        rest.cancelScheduleOrganisationsEventsRequests()
    }

    render() {
        return <div className="event-container">
            <EventForm />
            <EventsList />

        </div>

    }
}
export default EventsPage
