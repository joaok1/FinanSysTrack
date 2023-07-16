"use strict";

import axios from "axios";

const instance = axios.create({
  baseURL: 'http://localhost:1081/api/usuarios/auth'
});

export default instance;