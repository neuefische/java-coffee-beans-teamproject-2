import PersonType from "../../../../Type/PersonType.tsx";
import Person from "./PersonList/Person.tsx";

export default function PersonList({legend, people}: { legend: string, people: PersonType[] }) {
    return <fieldset className="person-list">
        <legend>{legend}</legend>
        {people?.map(
            (person) => <Person key={person.id} person={person}/>
        )}
    </fieldset>
}