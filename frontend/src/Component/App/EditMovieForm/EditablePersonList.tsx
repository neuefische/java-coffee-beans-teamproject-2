import PersonType from "../../../Type/PersonType.tsx";
import Person from "../Details/MovieDetails/PersonList/Person.tsx";
import AutoCompleteInput from "./AutoCompleteInput.tsx";
import {useState} from "react";

export default function EditablePersonList({legend, staff, setStaff, autocompletionUrl}: {
    legend: string,
    staff: PersonType[],
    setStaff: (staffList: PersonType[]) => void,
    autocompletionUrl: string
}) {

    const [person, setPerson] = useState<PersonType>({id: "", name: ""});

    const addPerson = function () {
        setStaff(
            [
                ...staff,
                person
            ]
        );
    }

    return (
        <fieldset className="person-list">
            <legend>{legend}</legend>
            <AutoCompleteInput autocompletionUrl={autocompletionUrl} person={person} setPerson={setPerson}/>
            <button onClick={addPerson}>Add</button>
            {Array.isArray(staff) && staff.length > 0 ? (
                staff.map((person) => <Person key={person.id} person={person}/>)
            ) : (
                <div>No people available</div>
            )}
        </fieldset>
    );
}
