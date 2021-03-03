var messagePlugin = {
	name: "",
	send: function(user, message){ return message; },
	get: function(user, message){ return message; }
};

var pluginManager = {
	plugins: [messagePlugin],
	register: function(plugin){
		plugins.push(plugin);
	}
};