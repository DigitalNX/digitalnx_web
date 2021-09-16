<template>
	<Dashboard>
		<div class="container-fluid">
			<div class="page-title">
				<h3>Notes</h3>
				<router-link to="new_note">
					<button type="button">New Note</button>
				</router-link>
			</div>
			<div v-if="isAvailable" class="row">
				<div class="col">
					<div v-if="notes.length != 0" class="row align-items-start">
						<NoteWidget
							v-for="note in notes"
							:key="note.id"
							:title="note.title"
							:body="note.body"
							:id="note.id"
							v-on:notes-list-update="updateNotes"
						/>
						</div>
				</div>
			</div>
		</div>
	</Dashboard>
</template>

<script>
import Dashboard from "./Dashboard.vue"
import NoteWidget from "../components/Widgets/NoteWidget.vue"

export default {
	name: "Notes",
	components: {
		Dashboard,
		NoteWidget,
	},
	data() {
		return {
			notes: [],
			isAvailable: Boolean
		};
	},
	methods: {
		updateNotes() {
			this.sendGETRequest("/notes/").then((data) => {
				this.notes = data['_embedded']['noteList'];
				this.isAvailable = true;
			});
		},
	},
	created() {
		this.updateNotes()
	},
};
</script>

<style scoped>
.page-title button {
	border: none;
	margin-left: 10px;
	font-size: 20px;
	background-color: #ededed;
	color: black;
}
</style>
