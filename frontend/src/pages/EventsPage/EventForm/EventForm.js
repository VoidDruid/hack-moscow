import React from 'react'
import './style.css'
import {observer} from "mobx-react";
import {eventFormStore} from "../../../store/EventFormStore";
import {preventDefault} from "leaflet/src/dom/DomEvent";

@observer
class EventForm extends React.Component {

    handleChangeTitle = (e) => this.title = e.target.value;
    handleChangeDescription = (e) => eventFormStore.description = e.target.value;
    handleChangeDate = (e) => eventFormStore.date = e.target.value;
    render() {
        return <form className="event-form" onSubmit={(e) => e.preventDefault()}>
            <h2>Add new event</h2>
                <label htmlFor="event-form-title-field">
                    <h3>Title</h3>
                <input
                    value={eventFormStore.title}
                    type="text"
                    id="event-form-title-field"
                    placeholder="Type title for the new event"
                    onChange={this.handleChangeTitle}
                />
                </label>
                <label htmlFor="event-form-description-field">
                    <h3>description</h3>
                <textarea
                    value={eventFormStore.description}
                    type="text"
                    id="event-form-description-field"
                    placeholder="Describe your event"
                    onChange={this.handleChangeDescription}
                />
                </label>
                <label htmlFor="event-form-date-field">
                    <h3>Date</h3>
                    <input
                        value={eventFormStore.date}
                        type="date"
                        id="event-form-description-field"
                        placeholder="Select the date"
                        onChange={this.handleChangeDate}
                    />
                </label>
                <button className="event-form-submit" onClick={eventFormStore.submit}>
                    Create event
                </button>
            </form>
    }
}

export default EventForm;
