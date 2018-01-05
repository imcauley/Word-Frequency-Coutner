var win			= window,
	d			= win.d || document,
	adx_inif	= (win != top),
	b			= "ad2",
	Y			= win.Y,
	q			= (win.q && win.cacheBust) ? (win.q + win.cacheBust) : q,
	scriptId	= d.getElementById("load_wrapper"),		
	proto		= (scriptId && (scriptId.src.indexOf("https://")!= -1)) ? "https:" : "http:",
	u			= win.u || "",
	uid			= win.uid || "",
	buffer		= ["<scr", "ipt type='text/javascr", "ipt' src='", proto, "//", u, "/", b, ".js?q=", q, "'></scr", "ipt>"],
	p 			= ["U","D","I"],
	i 			= 0,
	urlBuffer, item, gitem, gitemid, a;

function load_ad() {
    if (adx_inif) {
        if (Y && Y.SandBox && Y.SandBox.vendor) {
        	d.write(buffer.join(""));
        	return;
        }
    	try {
            a = '' + top.location;
    	} catch(e) {}
        if (win.inFIF || win.isAJAX || a) {
            b 			= "ad-iframe";
            adxid 		= Math.random(); 
            buffer[7]	= b;
            d.write(buffer.join(""));
            return;
        } 
        if (r && uid) {
			urlBuffer = [r, "/adx-iframe-v2.html?ad=", u ];
 			while (item = p[i++]) {
				gitemid	= "adx_" + item + "_" + uid;
				gitem 	= win[gitemid];
				if (gitem)
					urlBuffer.push("&", gitemid, "=", escape(gitem));
			}
            u = urlBuffer.join("");
			d.write('<IFRAME NAME="adxi" SRC="', u, '" WIDTH=1 HEIGHT=1 MARGINWIDTH=0 MARGINHEIGHT=0 FRAMEBORDER=0 SCROLLING="no" hidefocus="true" tabindex="-1"></IFRAME>');
			return;
		}
    }
    d.write(buffer.join(""));
}

load_ad();