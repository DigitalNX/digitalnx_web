<template>
	<Dashboard>
		<DashboardLayout />
	</Dashboard>
</template>

<script>
import Dashboard from './Dashboard.vue'
import DashboardLayout from '../components/DashboardLayout.vue';

export default {
	name: 'Home',
	components: {
		Dashboard,
		DashboardLayout
	}
}
</script>