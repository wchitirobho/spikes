// @flow

import React from 'react'
import {
  Navigator,
  StyleSheet,
  ListView
} from 'react-native'

var IOSButtonComponent = require('../common/button-ios.js')

var DebugNativeComponentScene = React.createClass({
  statics: {
    navigatorID () {
      return 'debug-native-component-identifier'
    },
    navigatorTitle () {
      return 'Native Components'
    }
  },

  propTypes: {
    navigator: React.PropTypes.instanceOf(Navigator).isRequired
  },

  getInitialState: function () {
    var ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2})
    let rows = ['Rick', 'And', 'Morty']
    return {
      dataSource: ds.cloneWithRows(rows)
    }
  },

  render () {
    return (
      <ListView
        dataSource={this.state.dataSource}
        enableEmptySections={true}
        renderRow={this._renderRow}
        style={styles.list}
        contentContainerStyle={styles.container}
      />
    )
  },

  _renderRow (rowData: string) {
    return <IOSButtonComponent text={rowData} style={{height: 60, width: 160}}/>
  }
})

const styles = StyleSheet.create({
  list: {
    backgroundColor: '#F5FCFF'
  },
  container: {
    alignItems: 'flex-start'
  },
  text: {
    fontSize: 20,
    color: 'black',
    margin: 8
  }
})

module.exports = DebugNativeComponentScene
