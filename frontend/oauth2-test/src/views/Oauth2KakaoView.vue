<template>
  <div>
    <h2>Kakao Oauth2 Test</h2>
    <a href="http://localhost:8081/oauth2/authorization/kakao?redirect_uri=http://localhost:3080"
      >Login Test</a
    >
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  setup() {
    return {}
  }
})
</script>

<style scoped></style>
