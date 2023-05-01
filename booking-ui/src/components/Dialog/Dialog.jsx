import './dialog.css'

function Dialog({ message, onDialog, itemId }) {
    return (
        <div
            className='dialogContainer'
            onClick={() => onDialog(false, itemId)}
        >
            <div
                onClick={(e) => e.stopPropagation()}
                className='dialogWrapper'
            >
                <h3>{message}</h3>
                <div className='buttonsContainer'>
                    <button
                        onClick={() => onDialog(true, itemId)}
                        className='btn confirmBtn'
                    >
                        Yes
                    </button>
                    <button
                        onClick={() => onDialog(false, itemId)}
                        className='btn cancelBtn'
                    >
                        No
                    </button>
                </div>
            </div>
        </div>
    );
}
export default Dialog;
