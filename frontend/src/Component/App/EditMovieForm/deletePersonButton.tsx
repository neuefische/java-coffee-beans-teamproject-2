import PersonType from "../../../Type/PersonType.tsx";
import axios from "axios";
type RelationType = {
    personId: string;
    movieId: string;
}
export default function DeletePersonButton({person, setStaff, staff, deleteUrl, movieId}: {
    person: PersonType,
    setStaff: (staffList: PersonType[]) => void,
    staff: PersonType[],
    deleteUrl: string,
    movieId: string
}) {

    const handleDelete = function (){
        setStaff(staff.filter((filteredPerson) => filteredPerson !== person));

        if (!movieId) return;
        const relation : RelationType = {
            personId: person.id,
            movieId: movieId
        }
        axios.delete(`${deleteUrl}`, {
            data: relation
        });
    }
    return <button onClick={handleDelete}>delete</button>
}