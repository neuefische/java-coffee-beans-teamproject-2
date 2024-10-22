import PersonType from "../../../../Type/PersonType.tsx";
import Person from "./PersonList/Person.tsx";

export default function PersonList({ legend, people }: { legend: string, people: PersonType[] }) {
    return (
        <fieldset className="person-list">
            <legend>{legend}</legend>
            {Array.isArray(people) && people.length > 0 ? (
                people.map((person) => <Person key={person.id} person={person} />)
            ) : (
                <div>No people available</div>
            )}
        </fieldset>
    );
}
