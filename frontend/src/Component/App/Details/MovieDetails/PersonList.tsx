import Person from "../../../../Type/Person.tsx";

export default function PersonList({data}: { data: Person[] }) {
    return <div>
        {data?.map(
            (value) => <div>{value.name}</div>
        )}
    </div>
}