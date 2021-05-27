import x_icon from "../Images/x_icon.png"
import { Clear } from "@material-ui/icons";

const Popup = ({title, inputs, setInputs, handleSubmit}) => {
  const changeInputs = (event, id) => {
    const inputs_new = [...inputs];
    const index = inputs.findIndex(item => item.id === id);
    inputs_new[index].value = event.target.value;
    setInputs(inputs_new);
  }

  const handleClose = () => {
    const popup = document.getElementsByClassName("popup")[0];
    popup.style.display = "none";
  }

  return (
    <div className="popup">
      <h1>{title}</h1>
      <button className="close" onClick={handleClose}><Clear /></button>
      <form onSubmit={handleSubmit}>
        {inputs.map((item) => (
          <label key={item.id} for={item.name}>
            {item.label}:
            <input 
              id={item.name} 
              required={item.required}
              {...(item.list === true ? {} : {value: item.value })}
              onChange={(e) => {changeInputs(e, item.id)}}
              {...(item.list === true ? { list: item.datalist_id,
              autoComplete: 'off' } : {})}
            />

            {item.list === true && 
              <datalist id={item.datalist_id}>
              {item.datalist.map(value => (
                <option key={item.datalist.findIndex(val => val === value)} value={value}/>
              ))}
              </datalist>
            }


          </label>
        ))}
        <button type="submit">submit</button>
      </form>
    </div>
  );
}
 
export default Popup;