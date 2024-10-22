import PersonType from "../../../../../Type/PersonType.tsx";

export default function Person({person}: { person: PersonType }) {
    return <input value={person.name} disabled/>
}