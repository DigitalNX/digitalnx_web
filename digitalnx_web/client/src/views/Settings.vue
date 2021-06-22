<template>
  <Dashboard>
    <div class="container-fluid">
      <div class="page-title">
        <h3>Settings</h3>
      </div>
      <div class="security-panel">
        <p>Security Mode:</p>
          <div class="btn-group btn-group-toggle">
            <input type="radio" class="btn-check" id="normal-mode" value="NORMAL" name="scheduler" :checked="selectedSecMode == 'NORMAL'" 
                v-model="selectedSecMode" v-on:input="changeSecMode"> 
            <label class="btn btn-outline-primary shadow-none" for="normal-mode">Normal</label>

            <input type="radio" class="btn-check" id="security-mode" value="SECURITY" name="scheduler" :checked="selectedSecMode == 'SECURITY'" 
                v-model="selectedSecMode" v-on:input="changeSecMode"> 
            <label class="btn btn-outline-primary shadow-none" for="security-mode">Security</label>
          </div>
      </div>
      <br>
      <div class="page-body">
        <h6 class="text-muted">
          Select your preferred dashboard layout from the list below.
        </h6>
      </div>
      <div v-if="isAvailable" class="layout-settings">
        <div class="row">
          <template v-for="list in lists" :key="list.index">
            <div class="col-sm text-center">
              <div class="list">
                <h4>{{ list.title }}</h4>
                <hr />
                <ul>
                  <li v-for="item in list.items" :key="item.index" v-on:click="selectItem(item, list)" class="item" v-bind:class="{isSelected: isSelected(item)}">
                    <h5>{{ capitalize(item.name) + ' (ID: ' + item.id + ')'}}</h5>
                </li>
                </ul>
              </div>
            </div>
          </template>
        </div>
        <div class="row justify-content-center">
          <button v-on:click="moveItem" type="button" class="btn btn-outline-primary btn-m move-button">
            Move Item
          </button>
        </div>
      </div>
        <div v-if="noItemSelected" class="error-message"> 
          * You must select an item from the lists.
        </div>
    </div>

  </Dashboard>
</template>
    
<script>
import Dashboard from './Dashboard.vue'

export default {
  name: "Settings",
  components: {
    Dashboard
  },
  data() {
    return {
      settings: [],
      selectedSecMode: String,
      layout: [],
      available_widgets: [],
      home_widgets: [],
      lists: [],
      selectedItems: [],
      noItemSelected: false,
      isAvailable: Boolean
    };
  },
  created() {
    this.sendGETRequest("/settings").then((settings) => {
        this.selectedSecMode = settings['sec_mode'];
    });
    this.sendGETRequest("/layout/").then((layout_data) => {
      this.sendGETRequest("/widgets/").then((widgets_data) => {
        let widgets = widgets_data;
        this.layout = layout_data;
        for (const item of widgets) {
          if (!this.layout.some((e) => e.name === item.name && e.id === item.id)) {
            this.available_widgets.push(item);
          }
        }
        this.selectedItems = [];
        this.updateLists();
        this.isAvailable = true;
      });
    });
  },
  methods: {
    selectItem(item, list) {
      if (!this.selectedItems.some(obj => obj.item === item && obj.list === list )) {
        this.selectedItems.push({'item': item, 'list': list})
      } else {
        this.selectedItems = this.selectedItems.filter(obj => obj.item !== item || obj.list !== list);
      }
    },
    isSelected(item) {
      if(this.selectedItems == null) return false;
      for(const selectedItem of this.selectedItems) {
        if(selectedItem.item === item) return true;
      }
      return false;
    },
    moveItem() {
      if(this.selectedItems ==  null) {
          this.noItemSelected = true;
      } else {
        for(const selectedItem of this.selectedItems) {
          let currentIndex = this.lists.findIndex(obj => obj.name === selectedItem.list.name)
          let nextIndex = (currentIndex+1) % this.lists.length;

          let item = selectedItem.item;
          this.lists[nextIndex].items.push(item);
          this.lists[currentIndex].items = this.lists[currentIndex].items.filter(ele => (ele.id !== item.id || ele.name !== item.name))
          if(this.lists[nextIndex].name == "home_layout") {
            this.addLayoutItem(item.name, item.id);
          } else if (this.lists[nextIndex].name == "available_widgets") {
            this.removeLayoutItem(item.name, item.id);
          }
        }
        this.selectedItems = [];
        this.noItemSelected = false;
      }
    },
    updateLists() {
      this.lists = []
      this.lists.push({
        name: "home_layout",
        title: "Home Layout",
        items: this.layout,
      });
      this.lists.push({
        name: "available_widgets",
        title: "Widgets List",
        items: this.available_widgets,
      });

    },
    async addLayoutItem(item_name, item_id) {
      this.sendPOSTRequest("/layout/new",{
        name: item_name,
        id: item_id
      });
    },
    async removeLayoutItem(item_name, item_id) {
      this.sendPOSTRequest("/layout/delete", {
        name: item_name,
        id: item_id
      });
    },
    capitalize(str) {
      if (typeof str === 'string') {
        return str.charAt(0).toUpperCase() + str.slice(1)
      }
    },
    changeSecMode() {
      if(this.selectedSecMode == 'SECURITY') {
        this.sendPOSTRequest("/settings/change", {
          sec_mode: 'NORMAL'
        });
      } else if (this.selectedSecMode == 'NORMAL') {
        this.sendPOSTRequest("/settings/change", {
          sec_mode: 'SECURITY'
        });
      }
    }
  },
};
</script>

<style scoped>
.page-body {
  margin-top: 10px;
  margin-left: 6px;
}
.layout-settings {
  margin-left: 50px;
  margin-right: 350px;
}
.security-panel * {
  display: inline;
  margin-left: 10px;
}
.security-panel p {
  margin-right: 15px;
}

h6 {
  margin-left: 5px;
}
input {
  display: block;
  margin: 10px;
}
.flex-container {
  display: flex;
  flex: 1;
  width: 100%;
  overflow-x: scroll;
}
.flex-container .list {
  flex-flow: column;
  flex: 1;

  min-width: 250px;
  max-width: 350px;
  min-height: 150px;
  margin: 0 15px;
  padding: 8px;
  transition: all 0.2s linear;
}
.row {
  margin-top: 30px;
}
ul {
  list-style-type: none;
}
li {
  margin: 4px;
}
li h5{
  margin-top: 3px;
  margin-bottom: 5px;
}
.layout-settings h4 {
  margin-bottom: 20px;
}
.p-2 {
  margin: 30px;
}
.item {
  cursor: move;
  padding-top: 2px;
  padding-bottom: 1px;
}
.isSelected{
  background-color: #e0e0e0;
  border-radius: 25px;
}
.error-message {
  color:red;
  margin-top: 25px;
  margin-bottom: 75px;
}
.move-button {
  margin-bottom: 50px;
}
</style>