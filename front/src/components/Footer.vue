<!-- eslint-disable vue/multi-word-component-names -->
<template lang="pug">
div
  <v-footer class="d-flex flex-column" style="background-color:#409EFF; color:#fff; font-weight:bold; font-size:16px;">
    <div class="bg-teal d-flex w-100 align-center px-2">
        <strong>Connect to my social networks!</strong>
        div(style="margin-left:10px;")
            a(href="https://contate.me/mirandadev")
                img(width="26" height="26" src="https://img.icons8.com/color/48/whatsapp--v1.png" alt="whatsapp--v1")
            a(href="https://instagram.com/joaovictorsouzamiranda?utm_source=qr&igshid=NGExMmI2YTkyZg%3D%3D")
                img(width="26" height="26" src="https://img.icons8.com/color/48/instagram-new--v1.png" alt="instagram-new--v1")
            a(href="https://www.linkedin.com/in/joao-victor-souza-7573a1121")
                img(width="26" height="26" src="https://img.icons8.com/color/48/linkedin.png" alt="linkedin")
    <v-spacer></v-spacer>
    </div>

    <div class="px-2 py-2 bg-black text-center w-100">
        span {{ new Date().getFullYear() }} — <strong>MirandaDev</strong>
    </div>
  </v-footer>

</template>
<script>
export default {
    setup() {
        
    },
}
</script>

<style scoped>

</style>
