const Footer = () => {
    return (
      <div className="footer">
        <h1>Dira</h1>
        <div className="links">
          <a href="/">Features</a>
          <a href="/pricing">Pricing</a>
          <a href="/contact">Contact</a>
        </div>
        <input type="text" placeholder="Email Adress" style={{margin:0,borderWidth:"thin"}}></input>
        <a href="/subscribe">SUBSCRIBE</a>
        
      </div>
    );
  }
   
  export default Footer;