<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Page to monitor DoneDeal adds</title>
</head>
<body>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript">
		function createInstructions() {
			var instructions_ta = document.createElement("textarea");
			instructions_ta.setAttribute("align", "center");
			instructions_ta.setAttribute("rows", "9");
			instructions_ta.setAttribute("cols", "100");
			var instructions_tn = document
					.createTextNode("INSTRUCTIONS:\n"
							+ "1. Go to www.donedeal.ie\n"
							+ "2. Find items you want to monitor\n"
							+ "3. Copy the URL from the top or your web browser\n"
							+ "4. Go back to Ddmonitor page and click ADD button\n"
							+ "5. Paste the URL into the URL field\n"
							+ "6. Name your monitor, provide your Email address and Frequency in seconds you want your monitor to refresh\n"
							+ "7. Click START - you will get an email when new adds come in");
			instructions_ta.appendChild(instructions_tn);
			document.body.appendChild(instructions_ta);
		}

		var body = document.body;
		
		function createUserTable() {
			var u_tbl = document.createElement('table');
			u_tbl.setAttribute('id', 'user_table');
			
			//label
			var tr_user_email = u_tbl.insertRow();
			var td_email_label = tr_user_email.insertCell();
			td_email_label.appendChild(document.createTextNode('Enter your e-mail here: '));
			td_email_label.setAttribute('align', 'center');
			//text field
			var td_input_email = tr_user_email.insertCell();
			var td_input_email_text = document.createElement("input");
			td_input_email.appendChild(td_input_email_text);
			td_input_email_text.setAttribute('type', "text");
			td_input_email_text.setAttribute('id', "user_email");
			td_input_email_text.setAttribute('value', '');
			td_input_email_text.setAttribute('style', "text-align: center");
			//submit button
			var td_cell_submit = tr_user_email.insertCell();
			var btn_submit = document.createElement("button");
			td_cell_submit.appendChild(btn_submit);
			var t = document.createTextNode("SUBMIT");
			btn_submit.style.backgroundColor = "green";
			btn_submit.appendChild(t);
			btn_submit.onclick = function() {
				removeTableAndButton()
				createTable();
				createAddButton();
				getExistingMonitors();
				document.getElementById("user_email").setAttribute('readonly', "readonly");
			};
			//change user button
			var td_cell_change_user = tr_user_email.insertCell();
			var btn_change_user = document.createElement("button");
			td_cell_change_user.appendChild(btn_change_user);
			var t = document.createTextNode("CHANGE USER");
			btn_change_user.style.backgroundColor = "yellow";
			btn_change_user.appendChild(t);
			btn_change_user.onclick = function visitPage(){
		        window.location=window.location.href;
		    };
			

			body.appendChild(u_tbl)
		}
		
		var tbl;		
		function createTable() {
			//create table
			tbl = document.createElement('table');
			tbl.setAttribute('id', 'table');
			//crate header
			createHeader();

			body.appendChild(tbl)
		}

		function createHeader() {
			//create rows and cell - column headers
			var tr_header = tbl.insertRow();
			var td_name = tr_header.insertCell();
			td_name.appendChild(document.createTextNode('Name'));
			td_name.setAttribute('align', 'center');
			var td_url = tr_header.insertCell();
			td_url.appendChild(document.createTextNode('URL'));
			td_url.setAttribute('align', 'center');
			var td_email = tr_header.insertCell();
			td_email.appendChild(document.createTextNode('E-mail'));
			td_email.setAttribute('align', 'center');
			var td_freq = tr_header.insertCell();
			td_freq.appendChild(document.createTextNode('Frequency(sec)'));
			td_freq.setAttribute('align', 'center');
			var td_status = tr_header.insertCell();
			td_status.appendChild(document.createTextNode('Status'));
			td_status.setAttribute('align', 'center');
		}

		function createNewTableRow(tbl) {
			var tr = tbl.insertRow();
			var td_input_name = tr.insertCell();
			var td_input_name_text = document.createElement("input");
			td_input_name.appendChild(td_input_name_text);
			td_input_name_text.setAttribute('type', "text");
			td_input_name_text.setAttribute('id', "new_name");
			td_input_name_text.setAttribute('style', "text-align: center");
			var td_input_url = tr.insertCell();
			var td_input_url_text = document.createElement("input");
			td_input_url.appendChild(td_input_url_text);
			td_input_url_text.setAttribute('type', "text");
			td_input_url_text.setAttribute('id', "new_url");
			td_input_url_text.setAttribute('value', '');
			td_input_url_text.setAttribute('style', "text-align: center");
			var td_input_email = tr.insertCell();
			var td_input_email_text = document.createElement("input");
			td_input_email.appendChild(td_input_email_text);
			td_input_email_text.setAttribute('type', "text");
			td_input_email_text.setAttribute('id', "new_email");
			td_input_email_text.setAttribute('value', document.getElementById('user_email').value);
			td_input_email_text.setAttribute('style', "text-align: center");
			td_input_email_text.setAttribute('readonly', "readonly");
			var td_input_freq = tr.insertCell();
			var td_input_freq_text = document.createElement("input");
			td_input_freq.appendChild(td_input_freq_text);
			td_input_freq_text.setAttribute('type', "text");
			td_input_freq_text.setAttribute('id', "new_freq");
			td_input_freq_text.setAttribute('value', '');
			td_input_freq_text.setAttribute('style', "text-align: center");
			var td_input_status = tr.insertCell();
			var td_input_status_text = document.createElement("input");
			td_input_status.appendChild(td_input_status_text);
			td_input_status_text.setAttribute('type', "text");
			td_input_status_text.setAttribute('value', '');
			td_input_status_text.setAttribute('style', "text-align: center");
			td_input_status_text.setAttribute('readonly', "readonly");
			//create buttons
						var td_cell_start = tr.insertCell();
			var btn_start = document.createElement("button");
			td_cell_start.appendChild(btn_start);
			var t = document.createTextNode("START");
			btn_start.style.backgroundColor = "green";
			btn_start.appendChild(t);
			btn_start.onclick = function() {
				$
						.post(
								"${pageContext.request.contextPath}/DoneDealAddsMonitoringServlet",
								{
									'user_email' : document.getElementById('user_email').value,
									'value' : 'start',
									'name' : document
											.getElementById('new_name').value,
									'url' : document.getElementById('new_url').value,
									'email' : document
											.getElementById('new_email').value,
									'frequency' : document
											.getElementById('new_freq').value
								}, function(data, textStatus) {
									$("#table tr").remove();
									createHeader();
									$.each(data, function(i, monitor) {
										createTableRow(monitor.name,
												monitor.url, monitor.email,
												monitor.frequency,
												monitor.status);
									});
								}, "json");
				document.getElementById('add_btn').disabled = false;
				document.getElementById('add_btn').style.backgroundColor = "orange";

			};
			var td_cell_stop = tr.insertCell();
			var btn_stop = document.createElement("button");
			td_cell_stop.appendChild(btn_stop);
			var t = document.createTextNode("STOP");
			btn_stop.style.backgroundColor = "yellow";
			btn_stop.appendChild(t);
			btn_stop.onclick = function() {
				var rowsNo = document.getElementById('table')
						.getElementsByTagName("tr").length;
				document.getElementById('table').deleteRow(rowsNo - 1);
				document.getElementById('add_btn').disabled = false;
				document.getElementById('add_btn').style.backgroundColor = "orange";
			};
			var td_cell_remove = tr.insertCell();
			var btn_remove = document.createElement("button");
			td_cell_remove.appendChild(btn_remove);
			var t = document.createTextNode("REMOVE");
			btn_remove.style.backgroundColor = "red";
			btn_remove.appendChild(t);
			btn_remove.onclick = function() {
				var rowsNo = document.getElementById('table')
						.getElementsByTagName("tr").length;
				document.getElementById('table').deleteRow(rowsNo - 1);
				document.getElementById('add_btn').disabled = false;
				document.getElementById('add_btn').style.backgroundColor = "orange";
			};
		}

		function createTableRow(name, url, email, frequency, status) {
			var tr = tbl.insertRow();
			var td_input_name = tr.insertCell();
			var td_input_name_text = document.createElement("input");
			td_input_name.appendChild(td_input_name_text);
			td_input_name_text.setAttribute('type', "text");
			td_input_name_text.setAttribute('value', name);
			td_input_name_text.setAttribute('style', "text-align: center");
			td_input_name_text.setAttribute('readonly', "readonly");
			var td_input_url = tr.insertCell();
			var td_input_url_text = document.createElement("input");
			td_input_url.appendChild(td_input_url_text);
			td_input_url_text.setAttribute('type', "text");
			td_input_url_text.setAttribute('value', url);
			td_input_url_text.setAttribute('style', "text-align: center");
			td_input_url_text.setAttribute('readonly', "readonly");
			var td_input_email = tr.insertCell();
			var td_input_email_text = document.createElement("input");
			td_input_email.appendChild(td_input_email_text);
			td_input_email_text.setAttribute('type', "text");
			td_input_email_text.setAttribute('value', email);
			td_input_email_text.setAttribute('style', "text-align: center");
			td_input_email_text.setAttribute('readonly', "readonly");
			var td_input_freq = tr.insertCell();
			var td_input_freq_text = document.createElement("input");
			td_input_freq.appendChild(td_input_freq_text);
			td_input_freq_text.setAttribute('type', "text");
			td_input_freq_text.setAttribute('value', frequency);
			td_input_freq_text.setAttribute('style', "text-align: center");
			td_input_freq_text.setAttribute('readonly', "readonly");
			var td_input_status = tr.insertCell();
			var td_input_status_text = document.createElement("input");
			td_input_status.appendChild(td_input_status_text);
			td_input_status_text.setAttribute('type', "text");
			td_input_status_text.setAttribute('value', status);
			td_input_status_text.setAttribute('style', "text-align: center");
			td_input_status_text.setAttribute('readonly', "readonly");
			//create buttons
			var td_cell_start = tr.insertCell();
			var btn_start = document.createElement("button");
			td_cell_start.appendChild(btn_start);
			var t = document.createTextNode("START");
			btn_start.style.backgroundColor = "green";
			btn_start.appendChild(t);
			btn_start.onclick = function() {
				$
						.post(
								"${pageContext.request.contextPath}/DoneDealAddsMonitoringServlet",
								{
									'user_email' : document.getElementById('user_email').value,
									'value' : 'start',
									'name' : td_input_name_text
											.getAttribute('value'),
									'url' : td_input_url_text
											.getAttribute('value'),
									'email' : td_input_email_text
											.getAttribute('value'),
									'frequency' : td_input_freq_text
											.getAttribute('value')
								}, function(data, textStatus) {
									$("#table tr").remove();
									createHeader();
									$.each(data, function(i, monitor) {
										createTableRow(monitor.name,
												monitor.url, monitor.email,
												monitor.frequency,
												monitor.status);
									});
								}, "json");
			};
			var td_cell_stop = tr.insertCell();
			var btn_stop = document.createElement("button");
			td_cell_stop.appendChild(btn_stop);
			var t = document.createTextNode("STOP");
			btn_stop.style.backgroundColor = "yellow";
			btn_stop.appendChild(t);
			btn_stop.onclick = function() {
				$
						.post(
								"${pageContext.request.contextPath}/DoneDealAddsMonitoringServlet",
								{
									'user_email' : document.getElementById('user_email').value,
									'value' : 'stop',
									'name' : td_input_name_text
											.getAttribute('value'),
									'url' : td_input_url_text
											.getAttribute('value'),
									'email' : td_input_email_text
											.getAttribute('value'),
									'frequency' : td_input_freq_text
											.getAttribute('value')
								}, function(data, textStatus) {
									$("#table tr").remove();
									createHeader();
									$.each(data, function(i, monitor) {
										createTableRow(monitor.name,
												monitor.url, monitor.email,
												monitor.frequency,
												monitor.status);
									});
								}, "json");
			};
			var td_cell_remove = tr.insertCell();
			var btn_remove = document.createElement("button");
			td_cell_remove.appendChild(btn_remove);
			var t = document.createTextNode("REMOVE");
			btn_remove.style.backgroundColor = "red";
			btn_remove.appendChild(t);
			btn_remove.onclick = function() {
				$
						.post(
								"${pageContext.request.contextPath}/DoneDealAddsMonitoringServlet",
								{
									'user_email' : document.getElementById('user_email').value,
									'value' : 'remove',
									'name' : td_input_name_text
											.getAttribute('value'),
									'url' : td_input_url_text
											.getAttribute('value'),
									'email' : td_input_email_text
											.getAttribute('value'),
									'frequency' : td_input_freq_text
											.getAttribute('value')
								}, function(data, textStatus) {
									$("#table tr").remove();
									createHeader();
									$.each(data, function(i, monitor) {
										createTableRow(monitor.name,
												monitor.url, monitor.email,
												monitor.frequency,
												monitor.status);
									});
								}, "json");
			};
			var td_cell_show_monitor = tr.insertCell();
			var btn_show_monitor = document.createElement("button");
			td_cell_show_monitor.appendChild(btn_show_monitor);
			var t = document.createTextNode("SHOW");
			btn_show_monitor.style.backgroundColor = "aqua";
			btn_show_monitor.appendChild(t);
			btn_show_monitor.onclick = function visitPage(){
				window.open(
						td_input_url_text.getAttribute('value'),
						  '_blank'
						);
		    };
		}
		
		function removeTableAndButton() {
			var table = document.getElementById("table");
			if(table)
				table.remove();
			var addButton = document.getElementById("add_btn");
			if(addButton)
				addButton.remove();
		};

		function createAddButton() {
			var btn = document.createElement("button");
			btn.setAttribute('id', 'add_btn');
			var t = document.createTextNode("ADD");
			btn.style.backgroundColor = "orange";
			btn.appendChild(t);
			btn.onclick = function() {
				createNewTableRow(tbl);
				document.getElementById('add_btn').disabled = true;
				document.getElementById('add_btn').style.backgroundColor = "gray";
			};
			document.body.appendChild(btn);
		};

		function getExistingMonitors() {
			$.getJSON('DoneDealAddsMonitoringServlet',
					{
				'user_email' : document.getElementById('user_email').value
					},
					function(data) {
				var table = $('<table>').appendTo(document.body);
				$.each(data, function(i, monitor) {
					createTableRow(monitor.name, monitor.url, monitor.email,
							monitor.frequency, monitor.status);
				});
			});

		}
	</script>

	<div align="center">
		<h1 align="center">DoneDeal.ie Monitoring Controller</h1>
		<script>
			createInstructions();
			createUserTable();
		</script>

	</div>
</body>
</html>