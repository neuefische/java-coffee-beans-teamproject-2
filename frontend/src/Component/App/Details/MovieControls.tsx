import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import {useState} from "react";

export default function MovieControls(
    {editModeEnabled, setEditModeEnabled}:
        { editModeEnabled: boolean, setEditModeEnabled: (state: boolean) => void }) {

    const [saveButtonDisabled, setSaveButtonDisabled] = useState<boolean>(false);
    const [deleteButtonDisabled, setDeleteButtonDisabled] = useState<boolean>(false);

    const params = useParams();
    const id: string | undefined = params.id;

    const navigate = useNavigate();

    const save = () => {
        setSaveButtonDisabled(true);
        axios.put(`/api/movie/${id}`).then(
            // () => navigate("/")
        ).catch(
            () => alert("Something went wrong")
        ).finally(
            () => {
                setEditModeEnabled(false);
                setSaveButtonDisabled(false);
            }
        );
    }

    const edit = () => {
        setEditModeEnabled(true);
    }

    const remove = async () => {
        setDeleteButtonDisabled(true);
        try {
            await axios.delete(`/api/movie/${id}`);
            await axios.delete(`/api/movie-actor/${id}`);
            await axios.delete(`/api/movie-director/${id}`);
            navigate("/");
        } catch (exception) {
            console.log(exception);
            alert("Something went wrong")
        } finally {
            setDeleteButtonDisabled(false);
        }
    }

    return (
        <div>
            <button onClick={save} hidden={!editModeEnabled} disabled={saveButtonDisabled}>Save</button>
            <button onClick={edit} hidden={editModeEnabled}>Edit</button>
            <button onClick={remove} disabled={deleteButtonDisabled}>Delete</button>
        </div>
    );
}