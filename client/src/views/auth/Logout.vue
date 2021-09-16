<script>
export default {
    name: 'Logout',
    created() {
        this.$store.dispatch('clearToken')
        this.$router.push('/login');
    }
}
</script>