import PersonType from "../../../Type/PersonType.tsx";
import Person from "../Details/MovieDetails/PersonList/Person.tsx";
import AutoCompleteInput from "./AutoCompleteInput.tsx";
import {FormEvent, useState} from "react";
import DeletePersonButton from "./deletePersonButton.tsx";

export default function EditablePersonList({legend, staff, setStaff, autocompletionUrl, deleteUrl, movieId}: {
    legend: string,
    staff: PersonType[],
    setStaff: (staffList: PersonType[]) => void,
    autocompletionUrl: string,
    deleteUrl: string,
    movieId: string
}) {

    const [person, setPerson] = useState<PersonType>({id: "", name: ""});

    const addPerson = function (event: FormEvent) {
        event.preventDefault();
        setStaff(
            [
                ...staff,
                person
            ]
        );
        setPerson({id: "", name: ""});
    }

    return (
        <fieldset className="person-list">
            <legend>{legend}</legend>
            <AutoCompleteInput autocompletionUrl={autocompletionUrl} person={person} setPerson={setPerson}/>
            <button onClick={addPerson}>Add</button>
            {Array.isArray(staff) && staff.length > 0 ? (
                    staff.map((person) => (
                        <div key={person.id} className="person-list">
                            <Person key={person.id} person={person} />
                            <DeletePersonButton person={person} setStaff={setStaff} staff={staff} deleteUrl={deleteUrl} movieId={movieId}/>
                        </div>
                    ))
                )
                : (
                <div>No people available</div>
            )}
        </fieldset>
    );
}