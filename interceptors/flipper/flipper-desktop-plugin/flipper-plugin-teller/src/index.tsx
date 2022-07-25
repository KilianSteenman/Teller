import {
  Text,
  Panel,
  ManagedDataInspector,
  createTablePlugin,
  TableBodyRow,
} from 'flipper-plugin';
import React from 'react';

type Row = {
  id: number;
  timestamp: string;
  framework: string;
  type: string;
  name: string;
  params: Map<string, any>;
};

const columns: DataTableColumn<Row>[] = [
  {
    key: 'timestamp',
    title: 'Timestamp',
    width: 100,
  },
  {
    key: 'framework',
    title: 'Framework',
    width: 100,
  },
  {
    key: 'type',
    title: 'Type',
    width: 100,
  },
  {
    key: 'name',
    title: 'Name',
    width: 100,
  },
  {
    key: 'params',
    title: 'Params',
  },
];

function paramsToKeyValuePairs(params: String) {
    return params.slice(1, -1)
        .split(/\s*,\s*/)
        .map(chunk => chunk.split("="));
}

function renderSidebar(row: Row) {
    let keyValuePairs = paramsToKeyValuePairs(row["params"])

    return (
  <>
    <style>{`
      h2 {
        padding-left:8px;
        padding-top:16px;
      }
      table.summary {
        width: 100%;
        table-layout: fixed;
      }
      table.params {
        width: 100%;
      }
      th, td {
       padding:8px;
      }
      tr:nth-child(even) {
        background-color: #F2F2F2;
      }
    `}</style>
    <div>
        <h2>Summary</h2>
        <table class="summary">
            <tr>
                <td>Framework</td>
                <td>{row["framework"]}</td>
            </tr>
            <tr>
                <td>Type</td>
                <td>{row["type"]}</td>
            </tr>
            <tr>
                <td>Name</td>
                <td>{row["name"]}</td>
            </tr>
        </table>
    </div>
    <div>
        <h2>Params</h2>
        <table class="params">
        {keyValuePairs.map((value) => (
        <tr>
            <td>{ value[0] }</td>
            <td>{ value[1] }</td>
        </tr>
        ))}
        </table>
    </div>
    </>
  );
}

module.exports = createTablePlugin<Row>({
  columns,
  key: 'id',
  method: 'newMeasurement',
  renderSidebar
});