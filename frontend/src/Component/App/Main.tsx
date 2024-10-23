import WatchedTab from "./Main/WatchedTab.tsx";
import WishlistTab from "./Main/WishlistTab.tsx";
import EditMovieForm from "./EditMovieForm.tsx";

export default function Main() {

    return (
        <>
            <div className={"main_container"}>
                <WatchedTab/>
                <WishlistTab/>
            </div>
            <EditMovieForm/>
        </>
    );
}