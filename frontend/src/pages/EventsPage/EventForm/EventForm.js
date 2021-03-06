import React from 'react'
import './style.css'
import {observer} from "mobx-react";
import {eventFormStore} from "../../../store/EventFormStore";
import {observable} from "mobx";

@observer
class EventForm extends React.Component {
    @observable isActiveForm = false;
    render() {
        return <form className={`event-form ${this.isActiveForm && 'event-form-active'}`} onSubmit={(e) => e.preventDefault()} onClick={(e) => this.isActiveForm = true}>
            <h2>Add new event</h2>
                <label htmlFor="event-form-title-field">
                    <h3>Title</h3>
                <input
                    value={eventFormStore.title}
                    type="text"
                    id="event-form-title-field"
                    placeholder="Type title for the new event"
                    onChange={(e) => eventFormStore.setTitle(e.target.value)}
                />
                </label>
                <label htmlFor="event-form-description-field">
                    <h3>description</h3>
                <textarea
                    value={eventFormStore.description}
                    id="event-form-description-field"
                    placeholder="Describe your event"
                    onChange={(e) => eventFormStore.setDescription(e.target.value)}
                />
                </label>
                <label htmlFor="event-form-date-field">
                    <h3>Date</h3>
                    <input
                        value={eventFormStore.date}
                        type="date"
                        id="event-form-description-field"
                        placeholder="Select the date"
                        onChange={(e) => eventFormStore.setDate(e.target.value)}
                    />
                </label>
                <button className="event-form-submit" onClick={() => eventFormStore.submit()}>
                    Create event
                </button>
            </form>
    }
}

export default EventForm;
