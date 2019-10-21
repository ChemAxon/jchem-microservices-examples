# Copyright 2019 ChemAxon Ltd.
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#     http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

FROM adoptopenjdk:8u222-b10-jdk-hotspot-bionic

RUN apt update && apt install -y libfontconfig1 && rm -rf /var/lib/apt/lists/*

ADD jws_unix_19.15.tar.gz /opt/chemaxon/
COPY license.cxl /opt/chemaxon/jws/license/license.cxl

