export default function ListHeaders({ isWatched }: { isWatched: boolean }) {
    return (
        <div className={"list-element-header"}>
            <span className={"movie-name"}>Name</span>
            <span className={"movie-rating-header"}>
                {isWatched ? "Rating" : "Priority"}
            </span>
            <span className={"edit-button"}>Actions</span>
        </div>
    );
}
